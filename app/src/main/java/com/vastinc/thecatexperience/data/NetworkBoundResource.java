package com.vastinc.thecatexperience.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NetworkBoundResource";
    private final MediatorLiveData<ResponseResource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
   public NetworkBoundResource() {
        result.setValue(ResponseResource.loading((ResultType)null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            result.setValue(ResponseResource.success(newData));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                result.setValue(ResponseResource.loading(newData));
            }
        });

        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                if (response.code() == 400) {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            try {
                                result.setValue(ResponseResource.error(response.errorBody().string(), newData));
                                result.removeSource(dbSource);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    saveResultAndReInit(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestType> call, final Throwable t) {
                onFetchFailed();
                Log.d(TAG, "onFailure called: ");
                result.removeSource(dbSource);
                result.addSource(dbSource, new Observer<ResultType>() {
                    @Override
                    public void onChanged(ResultType newData) {
                        result.setValue(ResponseResource.error(t.getMessage(), newData));
                    }
                });

            }
        });
    }

    @MainThread
    private void saveResultAndReInit(final RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), new Observer<ResultType>() {
                    @Override
                    public void onChanged(ResultType newData) {
                        result.setValue(ResponseResource.success(newData));
                    }
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<ResponseResource<ResultType>> getAsLiveData() {
        return result;
    }
}
