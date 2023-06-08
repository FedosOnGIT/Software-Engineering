package software.request;

import lombok.Data;

@Data
public class BaseRequest {
    private Integer hui;

    protected BaseRequest(BaseRequestBuilder<?, ?> b) {
        this.hui = b.hui;
    }

    public BaseRequest(Integer hui) {
        this.hui = hui;
    }

    public BaseRequest() {
    }

    public static BaseRequestBuilder<?, ?> builder() {
        return new BaseRequestBuilderImpl();
    }

    public static abstract class BaseRequestBuilder<C extends BaseRequest, B extends BaseRequestBuilder<C, B>> {
        private Integer hui;

        public B hui(Integer hui) {
            this.hui = hui;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "BaseRequest.BaseRequestBuilder(hui=" + this.hui + ")";
        }
    }

    private static final class BaseRequestBuilderImpl extends BaseRequestBuilder<BaseRequest, BaseRequestBuilderImpl> {
        private BaseRequestBuilderImpl() {
        }

        protected BaseRequestBuilderImpl self() {
            return this;
        }

        public BaseRequest build() {
            return new BaseRequest(this);
        }
    }
}
