package com.faizhasim.lightningtalk.akkapersistence

import akka.actor.ActorSystem

object LmsApp extends App {
  import TrainingCreditModel._
  println(akka.serialization.JavaSerializer)
  val system = ActorSystem("lms")
  val trainingCreditActor = system.actorOf(TrainingCreditActor.props())

  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))
  trainingCreditActor ! ResetTrainingCredit()
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(100))
  trainingCreditActor ! SpendTrainingCredit(4)
  trainingCreditActor ! SpendTrainingCredit(2)
  trainingCreditActor ! SpendTrainingCredit(45)
  trainingCreditActor ! AddTrainingCreditCoupon(TrainingCreditCoupon(10))

  Thread.sleep(5000)
  system.terminate()
}
