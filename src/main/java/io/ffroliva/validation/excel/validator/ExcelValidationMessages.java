package io.ffroliva.validation.excel.validator;

import am.ik.yavi.core.ViolationMessage;

public enum ExcelValidationMessages implements ViolationMessage {

    UNORDERED_LIST_FROM_SHEET("unordered.list.from.sheet", "\"{0}\" from sheet \"{1}\" must be in ascending order.") //
    ;

    private final String defaultMessageFormat;

    private final String messageKey;

    ExcelValidationMessages(String messageKey, String defaultMessageFormat) {
        this.messageKey = messageKey;
        this.defaultMessageFormat = defaultMessageFormat;
    }

    @Override
    public String defaultMessageFormat() {
        return defaultMessageFormat;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
