package com.baconmania.ghost

import com.baconmania.ghost.player.{PlayerOut, Player}

import scala.collection.mutable


/**
 * Created by glal on 8/2/15.
 */
object GameContext {

  private var players: mutable.DoubleLinkedList[Player] = _

  def apply(players: mutable.DoubleLinkedList[Player]) = {
    new GameContext(players)
  }

}

class GameContext(players: mutable.DoubleLinkedList[Player]) {

  private var currentPlayerIndex: Int = 0
  private val _wordSoFar: StringBuilder = new StringBuilder

  private def numPlayers = players.size

  def wordSoFar = _wordSoFar

  def startGame() = {
    while (players.nonEmpty) {
      players(currentPlayerIndex).takeTurn(this)
      currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers
    }
  }

  def challenge() = {
    val previousPlayer = players((-1 % numPlayers) + numPlayers)
    val intendedWord = previousPlayer.respondToChallenge(this)

    intendedWord match {
      case None =>
        // Previous player had nothin'
        val maybeStrikeout = previousPlayer.loseRound()
        handleStrikeout(previousPlayer, maybeStrikeout)
      case Some(word) =>
        // Current player loses a point
        val maybeStrikeout = players(currentPlayerIndex).loseRound()
        handleStrikeout(players(currentPlayerIndex), maybeStrikeout)
    }

    def handleStrikeout(player: Player, maybeStrikeout: Option[PlayerOut]) {
      maybeStrikeout match {
        case None =>
        case Some(strikeout) => // Player struck out. Remove it from the game.
          players = players.diff(Seq(player))
      }
    }
  }

  def addLetter(c: Char) = {
    _wordSoFar += c
  }
}