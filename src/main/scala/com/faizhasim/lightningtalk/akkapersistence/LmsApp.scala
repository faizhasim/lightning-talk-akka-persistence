package com.faizhasim.lightningtalk.akkapersistence

import akka.actor.ActorSystem

object LmsApp extends App {
  import TrainingCreditModel._
  println(akka.serialization.JavaSerializer)
  val system = ActorSystem("lms")
  val trainingCreditActor = system.actorOf(TrainingCreditActor.props())

  trainingCreditActor ! ResetTrainingCredit(50)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(2))
  trainingCreditActor ! SpendTrainingCredit(4)
  trainingCreditActor ! SpendTrainingCredit(22)
  trainingCreditActor ! SpendTrainingCredit(14)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))

  Thread.sleep(5000)
  system.terminate()
}
