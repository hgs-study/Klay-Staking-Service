package com.klaystakingservice.business.staking.domain.product.api;

import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm.*;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.response.dto.ResponseDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "5.Staking")
@RestController
@RequiredArgsConstructor
public class StakingController {

    private final StakingService stakingService;

    private final TokenService tokenService;

    @ApiOperation(value = "스테이킹 상품 등록", notes = "스테이킹 상품을 등록합니다.")
    @PostMapping("/stakings")
    public ResponseEntity<ResponseDTO> saveStaking(@Valid @RequestBody StakingForm.Request.Add add){
        final Token token = tokenService.findBySymbol("KLAY");
        final Staking staking = stakingService.create(add, token);
        stakingService.save(staking);

        Response.Find responseStaking = Response.Find.of(staking);
        return ApiResponse.set(HttpStatus.CREATED,"/",responseStaking.getId()+"번 상품이 정상적으로 등록되었습니다.");
    }

    @ApiOperation(value = "스테이킹 상품 삭제", notes = "스테이킹 상품을 삭제합니다.")
    @DeleteMapping("/stakings/{stakingId}")
    public ResponseEntity<ResponseDTO> deleteStaking(@PathVariable(name = "stakingId") Long stakingId){
        final Staking staking = stakingService.findById(stakingId);

        stakingService.deleteStaking(staking);

        return ApiResponse.set(HttpStatus.OK,"/",stakingId+"번 상품이 정상적으로 삭제되었습니다.");
    }

    @ApiOperation(value = "스테이킹 상품 상세 조회", notes = "스테이킹 상품을 상세 조회합니다.")
    @GetMapping("/stakings/{stakingId}")
    public Response.Find findStaking(@PathVariable(name = "stakingId") Long stakingId){
        final Staking staking = stakingService.findById(stakingId);

        return Response.Find.of(staking);
    }

    @ApiOperation(value = "스테이킹 상품 수정", notes = "스테이킹 상품을 수정합니다.")
    @PatchMapping("/stakings/{stakingId}")
    public ResponseEntity<ResponseDTO> modifyStaking(@PathVariable(name = "stakingId") Long stakingId,
                                                     @Valid @RequestBody StakingForm.Request.Modify modify){
        final Staking staking = stakingService.findById(stakingId);
        staking.toUpdate(modify.getName(), modify.getRewardAmount(), modify.getExpireDay());

        return ApiResponse.set(HttpStatus.OK,"/",stakingId+"번 상품이 정상적으로 수정되었습니다.");
    }
}
