package com.faizhasim.lightningtalk.akkapersistence

object TrainingCreditModel {

  case class TrainingCreditCoupon(arbitraryValue: Int)

  sealed trait TrainingCreditCommand
  case class AddTrainingCreditCoupon(trainingCreditCoupon: TrainingCreditCoupon) extends TrainingCreditCommand
  case class ResetTrainingCredit(initialAmount: Int = 100) extends TrainingCreditCommand
  case class SpendTrainingCredit(credit: Int) extends TrainingCreditCommand

  sealed trait TrainingCreditEvent
  case class TrainingCreditAdded(credit: Int) extends TrainingCreditEvent
  case class TrainingCreditRemoved(credit: Int) extends TrainingCreditEvent
  case class TrainingCreditReset(initialAmount: Int) extends TrainingCreditEvent

  // A state
  case class TrainingCreditWallet(credit: Int = 0) {
    def update(trainingCreditEvent: TrainingCreditEvent): TrainingCreditWallet = trainingCreditEvent match {
      case TrainingCreditAdded(amount) => copy(credit + amount)
      case TrainingCreditRemoved(amount) => copy(credit - amount)
      case TrainingCreditReset(initialAmount) => copy(initialAmount)
    }
  }

}
