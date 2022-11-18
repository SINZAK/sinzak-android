package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse
import retrofit2.Call
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapter<T : CResponse>(val type : Type): retrofit2.CallAdapter<T, Call<Result<T>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<T>): Call<Result<T>> {
        throw UnsupportedOperationException("사용되지 않는 함수입니다. adapt를 호출하지 마세요")
    }
    class Factory: retrofit2.CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): retrofit2.CallAdapter<*, *>? {
            if(getRawType(returnType) != Call::class.java) return null

            val responseType = getParameterUpperBound(0,returnType as ParameterizedType)

            if (getRawType(responseType) != Result::class.java) return null

            val successType = getParameterUpperBound(0, responseType as ParameterizedType)

            return CallAdapter<CResponse>(successType)

        }
    }
}