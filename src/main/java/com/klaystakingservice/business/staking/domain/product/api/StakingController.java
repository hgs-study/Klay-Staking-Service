package com.klaystakingservice.business.staking.domain.product.api;

import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm.*;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StakingController {

    private final StakingService stakingService;

    private final TokenService tokenService;

    @PostMapping("/staking")
    public ResponseEntity<MessageDTO> saveStaking(@Valid @RequestBody StakingForm.Request.Add add){
        final Token token = tokenService.findBySymbol("KLAY");
        final Staking staking = stakingService.create(add, token);
        stakingService.save(staking);

        Response.Find responseStaking = Response.Find.of(staking);
        return ApiResponse.set(HttpStatus.CREATED,"/",responseStaking.getId()+"번 상품이 정상적으로 등록되었습니다.");
    }


    @DeleteMapping("/staking/{stakingId}")
    public ResponseEntity<MessageDTO> deleteStaking(@PathVariable(name = "stakingId") Long stakingId){
        final Staking staking = stakingService.findById(stakingId);

        stakingService.deleteStaking(staking);

        return ApiResponse.set(HttpStatus.OK,"/",stakingId+"번 상품이 정상적으로 삭제되었습니다.");
    }


    @GetMapping("/staking/{stakingId}")
    public Response.Find findStaking(@PathVariable(name = "stakingId") Long stakingId){
        final Staking staking = stakingService.findById(stakingId);

        return Response.Find.of(staking);
    }


    @PatchMapping("/staking/{stakingId}")
    public ResponseEntity<MessageDTO> modifyStaking( @PathVariable(name = "stakingId") Long stakingId,
                                                     @Valid @RequestBody StakingForm.Request.Modify modify){
        final Staking staking = stakingService.findById(stakingId);
        staking.toUpdate(modify.getName(), modify.getRewardAmount(), modify.getExpireDay());

        return ApiResponse.set(HttpStatus.OK,"/",stakingId+"번 상품이 정상적으로 수정되었습니다.");
    }
}
