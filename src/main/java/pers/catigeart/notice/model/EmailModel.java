package pers.catigeart.notice.model;

import lombok.Data;


@Data
public class EmailModel {
    private String personal;
    private String subject;
    private String content;
}
