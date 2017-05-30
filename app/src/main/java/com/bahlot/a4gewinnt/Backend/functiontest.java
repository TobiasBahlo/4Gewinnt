package com.bahlot.a4gewinnt.Backend;

/**
 * Created by Toby on 30.05.2017.
 */

public class functiontest {

    public static void main(String[] args) {
        iGame game = new Game("Tobi", eColor.red, "Tania", eColor.yellow);
        System.out.println(game);





//		test for row win
	game.setCoin(2);
		System.out.println(game.winGame());
		game.setCoin(6);
	System.out.println(game.winGame());
		game.setCoin(3);
		System.out.println(game.winGame());
		game.setCoin(6);
		System.out.println(game.winGame());
		game.setCoin(4);
		System.out.println(game.winGame());
		game.setCoin(6);
		System.out.println(game.winGame());
		game.setCoin(4);
		System.out.println(game.winGame());
		game.setCoin(5);
		System.out.println(game.winGame());
		game.setCoin(1);
		System.out.println(game.winGame());
		System.out.println(game.getCurentBoard());



//		test for column win
//		game.setCoin(2);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		game.setCoin(3);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		game.setCoin(2);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		game.setCoin(2);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		game.setCoin(1);
//		System.out.println(game.winGame());
//		System.out.println(game.getCurentBoard());

    }

}
