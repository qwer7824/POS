package com.pos.exception;

public enum CustomExceptionEnum {
        PRODUCT_NOT_FOUND("해당하는 상품이 없습니다."),
        HOL_NOT_FOUND("해당하는 테이블이 없습니다."),
        SALE_NOT_FOUND("해당하는 주문이 없습니다.");

        private final String message;

        CustomExceptionEnum(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
