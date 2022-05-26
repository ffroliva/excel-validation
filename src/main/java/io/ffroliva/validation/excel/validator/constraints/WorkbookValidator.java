package io.ffroliva.validation.excel.validator.constraints;

import am.ik.yavi.arguments.ObjectValidator;
import am.ik.yavi.builder.ObjectValidatorBuilder;
import am.ik.yavi.core.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class WorkbookValidator {

    public static final ObjectValidator<InputStream, XSSFWorkbook> INPUT_STREAM_XSSF_WORKBOOK_VALIDATOR = ObjectValidatorBuilder
            .<InputStream>of("inputStream", Constraint::notNull)
            .build(is -> {
                try {
                    return new XSSFWorkbook(is);
                } catch (IOException e) {
                    log.error("{}", e.getMessage());
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        log.error("{}", e.getMessage());
                    }
                }
                return null;
            });
}
