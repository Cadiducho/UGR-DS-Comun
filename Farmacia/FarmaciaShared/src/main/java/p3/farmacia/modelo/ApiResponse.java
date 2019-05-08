package p3.farmacia.modelo;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Clase para envolver una respuesta del servidor rest
 * @param <T> Tipo del resultado
 */
public class ApiResponse<T> {

    /**
     * Check if the response was success or not.
     */
    private final Boolean success;

    /**
     * Optional. The message of the response.
     */
    private final String message;

    /**
     * Object responsed by the API.
     */
    private final T result;

    private static final Moshi moshi = new Moshi.Builder().build();

    /**
     * Serialize this response to json
     *
     * @return String that contains the serialized response
     */
    public String toJson() {
        //Only parametrize Result object if this is not null
        Type type = (result != null ? result.getClass() : Object.class);

        return toJson(type);
    }

    /**
     * Serialize this response to json
     *
     * @return String that contains the serialized response
     */
    public String toJson(Type type) {
        return moshi.adapter(Types.newParameterizedType(ApiResponse.class, type)).toJson(this);
    }

    /**
     * Create an ApiResponse object from a String
     *
     * @param <T>        Type of the result
     * @param source     String fetched from the API
     * @param resultType Class of the result
     * @return An ApiResponse object with that data
     * @throws IOException If source is malformed
     */
    public static <T> ApiResponse<T> from(String source, Class<T> resultType) throws IOException {
        JsonAdapter<ApiResponse<T>> resAdapter = moshi.adapter(Types.newParameterizedType(ApiResponse.class, resultType));
        return resAdapter.fromJson(source);
    }

    private ApiResponse(final Boolean success, final String message, final T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }


    @SuppressWarnings("all")
    public static class ApiResponseBuilder<T> {
        private Boolean success;
        private String message;
        private T result;

        ApiResponseBuilder() {
        }

        public ApiResponseBuilder<T> success(final Boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> result(final T result) {
            this.result = result;
            return this;
        }

        @SuppressWarnings("all")
        public ApiResponse<T> build() {
            return new ApiResponse<T>(success, message, result);
        }

    }

    @SuppressWarnings("all")
    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<T>();
    }

    /**
     * Check if the response was success or not.
     */
    public Boolean getSuccess() {
        return this.success;
    }

    /**
     * Optional. The message of the response.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Object responsed by the API.
     */
    public T getResult() {
        return this.result;
    }
}
