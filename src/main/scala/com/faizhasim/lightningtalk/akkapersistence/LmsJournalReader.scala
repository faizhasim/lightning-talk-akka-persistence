package com.faizhasim.lightningtalk.akkapersistence

import akka.actor.ActorSystem
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

object LmsJournalReader extends App {

  class RandomIntervalGenerator(val minDuration: FiniteDuration, val maxDuration: FiniteDuration) {
    def duration: FiniteDuration = {
      (Random.nextInt(maxDuration.toMillis.toInt - minDuration.toMillis.toInt) + minDuration.toMillis).millis
    }
  }

  import TrainingCreditModel._
  implicit val system: ActorSystem = ActorSystem("lms")
  import system.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()(system)

  val trainingCreditActor = system.actorOf(TrainingCreditActor.props())

  // WIll only show up for LevelDB (not MySQL)
  val queries = PersistenceQuery(system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)
  queries
    .persistenceIds()
    .map(id => system.log.info(s"Id received [$id]"))
    .to(Sink.ignore)
    .run()
  queries
    .eventsByPersistenceId("counter-persistent-actor-1")
    .map(eventEnvelope => system.log.info(s"Id [${eventEnvelope.persistenceId}] Event [${eventEnvelope.event}]"))
    .to(Sink.ignore)
    .run()


  val inSecondsRange = new RandomIntervalGenerator(1 second, 5 seconds)

  trainingCreditActor ! ResetTrainingCredit()
  system.scheduler.schedule(1 second, inSecondsRange.duration, trainingCreditActor, SpendTrainingCredit(21))
  system.scheduler.schedule(1 second, inSecondsRange.duration, trainingCreditActor, AddTrainingCreditCoupon(TrainingCreditCoupon(21)))

}
