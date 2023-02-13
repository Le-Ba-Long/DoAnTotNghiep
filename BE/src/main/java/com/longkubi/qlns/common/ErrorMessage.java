package com.longkubi.qlns.common;

public enum ErrorMessage {

    ID_NOT_EXIST(1, "ID Không Tồn Tại"),
    LIST_IS_EMPTY(2, "Danh Sách Rỗng"),
    OBJECT_NOT_EXIST(3, "Object Không Tồn Tại"),
    ACTION_NOT_EXIST(4, "Hành Động Không Tồn Tại"),
    OBJECT_CANNOT_EMPTY(5, "Object  Không Được Để Trống"),

    CODE_ALREADY_EXIST(6, "Mã Đã Tồn Tại"),

    NAME_EXIST(7, "Tên Đã Tồn Tại"),
    STATUS_NOT_SUPPORT(8, "Trạng Thái Không Hợp Lệ"),
    ACCOUNT_NAME_ALREADY_EXISTS(9, "Tên Tài Khoản Đã Tồn Tại"),
    UPDATE_FAILED(10, "Cập nhập thất bại"),
    THE_USERNAME_IS_EXISTED(11," Tài Khoản Đã Tồn Tại"),
    THE_EMAIL_IS_EXISTED(12," Email Đã Tồn Tại"),

    DATA_WRONG_FORM(13," Dữ Liệu Không Đúng Định Dạng"),
    MONTH_AND_YEAR_EXIST(13,"Tháng Và Năm Bạn Nhập Đã Tồn Tại"),
    SUCCESS(200, "Success"),
    ERROR(400, "Error");
    private final int code;
    private final String message;

    ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
