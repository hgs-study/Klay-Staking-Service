package com.klaystakingservice.business.staking.domain.product.api;

import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StakingController {

    private final StakingService stakingService;


    @PostMapping("/staking")
    public ResponseEntity<MessageDTO> saveStaking(@Valid @RequestBody StakingForm.Request.AddDTO addDTO){
        final Staking staking = stakingService.saveStaking(addDTO);

        return Response.ApiResponse(HttpStatus.CREATED,"/",staking.getId()+"번 상품이 정상적으로 등록되었습니다.");
    }


    @DeleteMapping("/staking/{stakingId}")
    public ResponseEntity<MessageDTO> deleteStaking(@PathVariable(name = "stakingId") Long stakingId){
        stakingService.deleteStaking(stakingId);

        return Response.ApiResponse(HttpStatus.OK,"/",stakingId+"번 상품이 정상적으로 삭제되었습니다.");
    }


    @GetMapping("/staking/{stakingId}")
    public StakingForm.Response.FindDTO getStaking(@PathVariable(name = "stakingId") Long stakingId){
        final Staking staking = stakingService.findById(stakingId);

        return StakingForm.Response.FindDTO.of(staking);
    }


    @PatchMapping("/staking/{stakingId}")
    public ResponseEntity<MessageDTO> modifyStaking( @PathVariable(name = "stakingId") Long stakingId,
                                                     @Valid @RequestBody StakingForm.Request.ModifyDTO modifyDTO){
        final Staking staking = stakingService.modifyStaking(stakingId, modifyDTO);

        return Response.ApiResponse(HttpStatus.OK,"/",staking.getId()+"번 상품이 정상적으로 수정되었습니다.");
    }
}
