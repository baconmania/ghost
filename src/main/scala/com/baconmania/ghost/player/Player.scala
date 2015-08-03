package com.baconmania.ghost.player

import com.baconmania.ghost.GameContext

/**
 * Created by glal on 8/2/15.
 */
trait Player {

  private var roundsLost: Int = 0
  val _name: String

  def name: String = _name

  def takeTurn(gc: GameContext): Unit

  def respondToChallenge(gc: GameContext): Option[String]

  final def loseRound(): Option[PlayerOut] = {
    roundsLost += 1
    roundsLost match {
      case 5 => Some(PlayerOut())
      case _ => None
    }
  }

}

case class PlayerOut()