package com.sov.service;

import com.sov.data.LikeData;
import com.sov.model.LikeModel;
import com.sov.model.RoleModel;
import com.sov.model.UserModel;
import com.sov.repository.LikeRepository;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LikeService {

    @Resource
    private LikeRepository likeRepository;

    @Resource
    private UserService userService;

    @Resource
    private InvestorService investorService;

    @Resource
    private CompanyService companyService;

    public void like(Long projectId, boolean positive) {
        Long fromId;
        UserModel user = userService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException());
        if (user.getRoles().contains(RoleModel.INVESTOR)) {
            fromId = investorService.getInvestorByUsername(user.getUsername()).orElse(null).getId();
        } else {
            fromId = companyService.getCompanyByUserUsername(user.getUsername()).orElse(null).getId();
        }
        List<LikeModel> likes = likeRepository.findAllByProjectIdAndFromId(projectId, fromId);
        likeRepository.deleteAll(likes);
        likeRepository.save(new LikeModel(projectId, fromId, positive));
    }

    public void unlike(Long projectId, boolean positive) {
        Long fromId;
        UserModel user = userService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException());
        if (user.getRoles().contains(RoleModel.INVESTOR)) {
            fromId = investorService.getInvestorByUsername(user.getUsername()).orElse(null).getId();
        } else {
            fromId = companyService.getCompanyByUserUsername(user.getUsername()).orElse(null).getId();
        }
        List<LikeModel> likes = likeRepository.findAllByProjectIdAndFromId(projectId, fromId);
        likeRepository.deleteAll(likes);
    }

    public LikeData getProjectLikes(Long id) {
        Long fromId;
        UserModel user = userService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException());
        if (user.getRoles().contains(RoleModel.INVESTOR)) {
            fromId = investorService.getInvestorByUsername(user.getUsername()).orElse(null).getId();
        } else {
            fromId = companyService.getCompanyByUserUsername(user.getUsername()).orElse(null).getId();
        }
        List<LikeModel> likes = likeRepository.findAllByProjectId(id);
        long likesCount = likes.stream().filter(LikeModel::isPositive).count();
        long dislikesCount = likes.stream().filter(l -> !l.isPositive()).count();
        boolean liked = likes.stream().anyMatch(l -> l.isPositive() && l.getFromId().equals(fromId));
        boolean disliked = likes.stream().anyMatch(l -> !l.isPositive() && l.getFromId().equals(fromId));
        return new LikeData(id, likesCount, dislikesCount, liked, disliked);
    }
}
