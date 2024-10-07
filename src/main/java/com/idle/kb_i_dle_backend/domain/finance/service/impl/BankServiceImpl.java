package com.idle.kb_i_dle_backend.domain.finance.service.impl;

import com.idle.kb_i_dle_backend.config.exception.CustomException;
import com.idle.kb_i_dle_backend.domain.finance.dto.BankDTO;
import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.finance.repository.BankRepository;
import com.idle.kb_i_dle_backend.domain.finance.service.BankService;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.global.codes.ErrorCode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final MemberService memberService;
    private final BankRepository bankRepository;

    @Override
    public List<BankDTO> getBankList() throws Exception {
        Member member = memberService.findMemberByUid(1);
        List<Bank> banks = bankRepository.findByUidAndDeleteDateIsNull(member);

        if (banks.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_BANK, "user dont have banks");
        }

        List<BankDTO> bankList = new ArrayList<>();
        for (Bank b : banks) {
            BankDTO bankDTO = BankDTO.convertToDTO(b);
            bankList.add(bankDTO);
        }

        return bankList;
    }

    @Override
    public BankDTO addBank(BankDTO bankDTO) throws ParseException {
        Member member = memberService.findMemberByUid(1);
        Bank savedBank = bankRepository.save(BankDTO.convertToEntity(member, bankDTO));

        return BankDTO.convertToDTO(savedBank);
    }

    @Transactional
    @Override
    public BankDTO updateBank(BankDTO bankDTO) throws ParseException {
        Member member = memberService.findMemberByUid(1);

        // Bank 조회
        Bank isBank = bankRepository.findByIndexAndDeleteDateIsNull(bankDTO.getIndex())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BANK, "user dont have banks"));

        // Bank의 소유자가 해당 User인지 확인
        if (!isBank.getUid().equals(member)) {
            throw new CustomException(ErrorCode.INVALID_OWNER, "this user dont have permission to modify this asset");
        }

        // 잔액만 수정되게
        isBank.setBalanceAmt(bankDTO.getBalanceAmt());

        Bank savedBank = bankRepository.save(isBank);
        return BankDTO.convertToDTO(savedBank);
    }

    @Transactional
    @Override
    public BankDTO deleteBank(Integer index) throws ParseException {
        Member member = memberService.findMemberByUid(1);

        // Bank 조회
        Bank isBank = bankRepository.findByIndexAndDeleteDateIsNull(index)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BANK, "Bank not found with id: " + index));

        // Bank의 소유자가 해당 User인지 확인
        if (!isBank.getUid().equals(member)) {
            throw new CustomException(ErrorCode.INVALID_OWNER, "this user dont have permission to modify this asset");
        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.format(new Date());
        isBank.setDeleteDate(new Date());

        Bank savedBank = bankRepository.save(isBank);
        return BankDTO.convertToDTO(savedBank);
    }
}
