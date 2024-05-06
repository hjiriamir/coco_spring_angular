package tn.esprit.coco.service;

import tn.esprit.coco.entity.Subscription;

import java.util.List;

public interface ISubscriptionServices {
    Subscription addSubscription(  Subscription Subscription);
    List<Subscription> getAllSubscription();
    Subscription getSubscription();
    void removeSub(Long idSub);
}
