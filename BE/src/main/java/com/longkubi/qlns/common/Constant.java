package com.longkubi.qlns.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constant {
    public static final String CANDIDATE_PROFILE_KEY = "candidate_profile";
    public static final String REGEX_CODE_LANGUAGE = "^MaNN[0-9]{4}$";
    public static final String REGEX_CODE_DEPARTMENT = "^MaPB[0-9]{4}$";

    public static final String REGEX_CODE_CERTIFICATE = "^MaBC[0-9]{4}$";

    public static final String REGEX_CODE_EMPLOYEE = "^MaNV[0-9]{4}$";
    public static final String REGEX_CODE_POSITION = "^MaCV[0-9]{4}$";

    public static final String REGEX_CODE_CANDIDATE_PROFILE = "^MaHS[0-9]{4}$";

    public static final String REGEX_CODE_RECRUIT = "^MaTD[0-9]{4}$";

    public static final String REGEX_CODE_CONTRACT = "^MaHĐ[0-9]{4}$";

    public static final String REGEX_CODE_SALARY = "^MaL[0-9]{4}$";

    public static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String REGEX_PHONE = "^(84|0[3|5|7|8|9])+([0-9]{8})$";
    public static final String Insert = "Insert";

    public static final String Delete = "Delete";

    public static final String Update = "Update";
    public static String titleBirthDay[] = {"STT","code", "fullName", "dateOfBirth", "sex", "position", "department.name"};
    public static String titleContract[] = {"STT","code", "fullName","position", "department.name","contract.code","contract.contractEffect"};

    public enum StatusType {
        NOT_APPROVED((byte) -1),//Không duyệt
        NEW_SAVE((byte) 1),//Lưu mới
        PENDING((byte) 2),//Chờ Duyệt (chưa được chấp thuận)
        APPROVED((byte) 3),//Đã phê duyệt
        PROCESSING((byte) 4),//Đang thực hiện

        FINISHED((byte) 5),//Đã kết thúc
        REJECT((byte) 6),//Từ Chối

        RECEIVE((byte) 7),//Tiếp Nhận

        PASS((byte) 8),// Pass Phỏng Vấn

        NOT_PASS((byte) 9),//Không Pass Phỏng Vấn
        FIX_REQUEST((byte) 10),// Yêu cầu chỉnh sửa
        WAITING_RE_APPROVED((byte) 11),// Chờ đợi approved (sau khi yêu cầu chỉnh sửa hoàn tất)

        TRY_JOB((byte) 12),//Thử  Việc

        END_TRY_JOB((byte) 13),// Kết Thúc Thử  Việc

        OFFICIAL_STAFF((byte) 14),// Nhân Viên Chính Thức

        RETIRED_FROM_WORK((byte) 15),// Nhân Viên Đã Nghỉ Việc

        FIRED((byte) 16),// Nhân Viên Đã Sa Thải

        PENDING_TREATMENT((byte) 17),// Chờ Xử Lí

        WAITING_FOR_INTERVIEW((byte) 18),// Chờ Phỏng Vấn

        CANDIDATE_PROFILE_CONVERSION((byte) 19)//Chuyển đổi hồ sơ ứng viên
        ;
        private final byte type;

        StatusType(final byte type) {
            this.type = type;
        }

        public static StatusType parse(Byte type) {
            if (type == null) {
                return null;
            }

            for (StatusType statusType : values()) {
                if (statusType.type == type) {
                    return statusType;
                }
            }

            return null;
        }

        public static List<Byte> getByDisplayStatus(byte displayStatus) {
            if (displayStatus == 1) {
                // <=2 + +4 + 7 + 9 + 10
                return Stream.of(NOT_APPROVED, NEW_SAVE, PENDING, PROCESSING, FIX_REQUEST, WAITING_RE_APPROVED)
                        .map(StatusType::getType)
                        .collect(Collectors.toList());
            } else if (displayStatus == 2) {
                // >=3
                return Stream.of(PENDING)
                        .map(StatusType::getType)
                        .collect(Collectors.toList());
            } else if (displayStatus == 3) {
                // >=3
                return Stream.of(APPROVED)
                        .map(StatusType::getType)
                        .collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
        }

        public static List<Byte> getAllStatus() {
            return Arrays.stream(values())
                    .map(StatusType::getType)
                    .collect(Collectors.toList());
        }

        public byte getType() {
            return type;
        }
    }

}


