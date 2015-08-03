package com.baconmania.ghost.player

import com.baconmania.ghost.GameContext
import org.apache.commons.collections4.trie.PatriciaTrie

import scala.collection.JavaConversions._
import scala.io.Source
import scala.util.Random

/**
 * Created by glal on 8/2/15.
 */
object AI {
  def apply(): AI = {
    val dictionary = new PatriciaTrie[Int]()
    val dictFile = Source.fromFile("/usr/share/dict/words").getLines()
    dictionary.putAll(mapAsJavaMap(dictFile.map(x => (x, 1)).toMap))
    println(dictionary.prefixMap("ob"))
    new AI("AI", dictionary)
  }
}

class AI(id: String, dictionary: PatriciaTrie[Int]) extends Player {
  override val _name = id
  var plannedWord: Option[String] = None

  override def takeTurn(gc: GameContext) = {
    val possibilities = dictionary.prefixMap(gc.wordSoFar.toString()).keySet
    if (possibilities.isEmpty) {
      plannedWord = None
      gc.challenge()
    } else {
      val rng = new Random
      plannedWord = Some(possibilities.toVector(rng.nextInt(possibilities.size())))
      gc.addLetter(plannedWord.get(gc.wordSoFar.length))
    }
  }

  override def respondToChallenge(gc: GameContext) = {
    plannedWord
  }

}
