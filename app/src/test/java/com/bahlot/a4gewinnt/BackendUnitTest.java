package com.bahlot.a4gewinnt;

import com.bahlot.a4gewinnt.Backend.Board;
import com.bahlot.a4gewinnt.Backend.Game;
import com.bahlot.a4gewinnt.Backend.eColor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test for Backend
 * By Tom Maier
 */

public class BackendUnitTest {


    /**
     * Unit to test whether null as a name throws exception
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void addANonamePlayer() throws Exception {
        Game game = new Game(null, eColor.red, "Tania", eColor.yellow);
    }

    /**
     * Unit to test whether colorless throws Exception
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void addColorlessPlayer() throws Exception {
        Game game = new Game("Tobi", eColor.none, "Tania", eColor.yellow);
    }

    /**
     * Unit to test whether null color throws Exception
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void addNullColorPlayer() throws Exception {
        Game game = new Game("Tobi", null, "Tania", eColor.yellow);
    }

    /**
     * Unit to test whether a move is correctly performed
     *
     * @throws Exception
     */
    @Test
    public void makeAMove() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);
        game.setCoin(0);

        Board board = game.getBoardObject();

        boolean expected = board.getBoard()[5][0].getColor() != eColor.none;

        System.out.println(board.toString());

        Assert.assertTrue(expected);
    }

    /**
     * Unit to test whether invalid setCoin input throws an exception
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void OutOfBoundMove() {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);
        game.setCoin(-4);
    }

    /**
     * Unit to test whether player turn switches after a move has been made
     *
     * @throws Exception
     */
    @Test
    public void correctContinuation() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);

        game.setCoin(0);
        game.setCoin(1);

        Board board = game.getBoardObject();

        boolean expected = (board.getBoard()[5][0].getColor() == eColor.red) &&
                (board.getBoard()[5][0].getColor() != eColor.yellow);

        Assert.assertTrue(expected);

    }

    /**
     * Unit to test whether game was won by horizontal arrangement of coins
     *
     * @throws Exception
     */
    @Test
    public void horizontalWin() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);

        game.setCoin(0);
        game.setCoin(5);

        game.setCoin(1);
        game.setCoin(5);

        game.setCoin(2);
        game.setCoin(5);

        game.setCoin(3);
        game.setCoin(5);

        game.setCoin(4);
        game.setCoin(5);

        boolean expected = game.winGame();

        Assert.assertTrue(expected);
    }

    /**
     * Unit to test whether game was won by vertical arrangement of coins
     *
     * @throws Exception
     */
    @Test
    public void verticalWin() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);

        game.setCoin(1);
        game.setCoin(2);

        game.setCoin(1);
        game.setCoin(5);

        game.setCoin(1);
        game.setCoin(5);

        game.setCoin(1);
        game.setCoin(4);

        game.setCoin(1);
        game.setCoin(5);

        boolean expected = game.winGame();

        Assert.assertTrue(expected);
    }

    /**
     * Unit to test whether game was won by diagonal arrangement of coins from left to right
     *
     * @throws Exception
     */
    @Test
    public void diagonalWinFromLeftToRight() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);

        game.setCoin(1);
        game.setCoin(2);

        game.setCoin(2);
        game.setCoin(3);

        game.setCoin(3);
        game.setCoin(5);

        game.setCoin(3);
        game.setCoin(4);

        game.setCoin(4);
        game.setCoin(4);

        game.setCoin(4);

        boolean expected = game.winGame();
        Assert.assertTrue(expected);
    }

    /**
     * Unit to test whether game was won by diagonal arrangement of coins from right to left
     *
     * @throws Exception
     */
    @Test
    public void diagonalWinFromRightToLeft() throws Exception {
        Game game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);
        game.setCoin(5);
        game.setCoin(4);

        game.setCoin(4);
        game.setCoin(3);

        game.setCoin(3);
        game.setCoin(0);

        game.setCoin(3);
        game.setCoin(2);

        game.setCoin(2);
        game.setCoin(2);

        game.setCoin(2);

        System.out.println(game.getCurentBoard().toString());
        boolean expected = game.winGame();
        Assert.assertTrue(expected);

    }
}