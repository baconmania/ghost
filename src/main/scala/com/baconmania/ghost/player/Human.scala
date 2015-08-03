package com.baconmania.ghost.player

import com.baconmania.ghost.GameContext

/**
 * Created by glal on 8/2/15.
 */
class Human(id: String) extends Player {
  override val _name = id

  override def takeTurn(gc: GameContext): Unit = ???

  override def respondToChallenge(gc: GameContext): Option[String] = ???

}
