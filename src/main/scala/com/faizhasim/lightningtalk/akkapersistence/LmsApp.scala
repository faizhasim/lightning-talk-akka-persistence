package com.faizhasim.lightningtalk.akkapersistence

import TrainingCreditModel._
import akka.actor.ActorSystem

object LmsApp extends App {
  val system = ActorSystem("lms")
  val trainingCreditActor = system.actorOf(TrainingCreditActor.props())

  trainingCreditActor ! ResetTrainingCredit(50)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))
  trainingCreditActor ! SpendTrainingCredit(4)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(2))
  trainingCreditActor ! SpendTrainingCredit(22)
  trainingCreditActor ! SpendTrainingCredit(14)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))

  Thread.sleep(5000)
  system.terminate()
}

