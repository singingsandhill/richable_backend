package com.idle.kb_i_dle_backend.income.dto;

import com.idle.kb_i_dle_backend.income.entity.Income;
import com.idle.kb_i_dle_backend.member.entity.User;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
    private Integer incomeId;
    private String type;
    private String incomeDate;
    private Long price;
    private String contents;
    private String memo;

    public static IncomeDTO convertToDTO(Income income) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new IncomeDTO(income.getIndex(), income.getType(), dateFormat.format(income.getDate()), income.getAmount(), income.getDescript(), income.getMemo());
    }

    public static Income convertToEntity(User user, IncomeDTO incomeDTO) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date iDate = (incomeDTO.getIncomeDate() != null)
                ? dateFormat.parse(incomeDTO.getIncomeDate())
                : null;  // null 값 유지
        return new Income(incomeDTO.getIncomeId(), user, incomeDTO.getType(), incomeDTO.getPrice(), iDate, incomeDTO.getContents(), incomeDTO.getMemo());
    }
}
