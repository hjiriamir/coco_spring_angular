package tn.esprit.coco.service;

import tn.esprit.coco.entity.Subscription;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.entity.TripStop;

import java.util.List;

public interface ISubscriptionServices {
    Subscription addSubscription(Long userId,  Subscription Subscription);
    List<Subscription> getAllSubscription();
    Subscription getSubscription(Long iduser);
    void removeSub(Long idSub);
}
