package com.pmp.nwms.util;

import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.model.SpecialLinkHolderModel;
import com.pmp.nwms.repository.PurchaseStatusRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseStatusUtil {
    public static Map<Long, String> getUsersSpecialLinks(List<Long> userIds, PurchaseStatusRepository purchaseStatusRepository) {
        Map<Long, String> map = new HashMap<>();
        List<PurchaseStatus> currentPlans = purchaseStatusRepository.findCurrentPlanByUserIds(userIds);
        if (currentPlans != null && !currentPlans.isEmpty()) {
            for (PurchaseStatus plan : currentPlans) {
                map.put(plan.getUserId(), plan.getSpecialLink());
            }
        }
        return map;
    }

    public static void setSpecialLinks(List<? extends SpecialLinkHolderModel> items, PurchaseStatusRepository purchaseStatusRepository){
        if (items != null && !items.isEmpty()) {
            List<Long> userIds = new ArrayList<>();
            for (SpecialLinkHolderModel item : items) {
                if (!userIds.contains(item.getCreatorId())) {
                    userIds.add(item.getCreatorId());
                }
            }
            Map<Long, String> usersSpecialLinks = getUsersSpecialLinks(userIds, purchaseStatusRepository);
            if(!usersSpecialLinks.isEmpty()){
                for (SpecialLinkHolderModel item : items) {
                    item.setSpecialLink(usersSpecialLinks.get(item.getCreatorId()));
                }
            }
        }
    }
}
