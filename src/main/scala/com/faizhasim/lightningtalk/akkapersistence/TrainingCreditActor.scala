package com.faizhasim.lightningtalk.akkapersistence

import akka.actor.{ActorLogging, Props}
import akka.persistence.{PersistentActor, Recovery, RecoveryCompleted, SnapshotOffer}

class TrainingCreditActor(r: Recovery = Recovery()) extends PersistentActor with ActorLogging {
  import TrainingCreditModel._
  override val persistenceId: String = "counter-persistent-actor-1"
  override val recovery: Recovery = r

  var state = TrainingCreditWallet()
  def updateState(event: TrainingCreditEvent): Unit = state = state.update(event)

  override def receiveRecover: Receive = {
    case event: TrainingCreditEvent =>
      log.info(s"Replaying event: $event")
      updateState(event)
    case SnapshotOffer(_, recoveredState: TrainingCreditWallet) =>
      log.info(s"Snapshot offered: $recoveredState")
      state = recoveredState
    case RecoveryCompleted =>
      log.info(s"Recovery completed. Current state: $state")
    case s => println(s"receiveRecover: $s")
  }

  val snapShotInterval = 3

  private def updateStateWithSnapshotting(event: TrainingCreditEvent) = {
    updateState(event)
    context.system.eventStream.publish(event)
    if (lastSequenceNr % snapShotInterval == 0 && lastSequenceNr != 0)
      saveSnapshot(state)
  }

  override def receiveCommand: Receive = {
    case AddTrainingCreditCoupon(coupon) =>
      persist(TrainingCreditAdded(coupon.arbitraryValue))(updateStateWithSnapshotting)
    case ResetTrainingCredit(initialAmount) =>
      persist(TrainingCreditReset(initialAmount))(updateStateWithSnapshotting)
    case SpendTrainingCredit(credit) =>
      persist(TrainingCreditRemoved(credit))(updateStateWithSnapshotting)
  }
}

object TrainingCreditActor {
  def props(recovery: Recovery = Recovery()) = Props(new TrainingCreditActor(recovery))
}