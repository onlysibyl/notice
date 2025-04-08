package pers.catigeart.notice.dto;

import lombok.Data;


@Data
public class SupplyDTO {
    private Integer id;
    private Integer belongingId;
    private String belongingName;
    private Integer roleId;
    private String roleName;
    private String content;
}
