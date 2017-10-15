/*
 *  Copyright (C) 2008-2009 Rev. Johnny Healey <rev.null@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wordhunt.wordhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.wordhunt.wordhunt.Synchronizer;
import com.wordhunt.wordhunt.game.Game;
import com.wordhunt.wordhunt.view.wordhuntView;

public class Playwordhunt extends AppCompatActivity implements com.wordhunt.wordhunt.Synchronizer.Finalizer {

	protected static final String TAG = "Playwordhunt";

	private Synchronizer synch;
	private Game game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String action = getIntent().getAction();
			switch (action) {
				case "com.wordhunt.wordhunt.NEW_GAME":
					newGame();
					break;
			}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			//case R.id.rotate:
			//	game.rotateBoard();
			//break;
			case R.id.end_game:
				game.endNow();
		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return game.getStatus() != Game.GameStatus.GAME_FINISHED;
	}

	private void newGame() {
		game = new Game(this);

		wordhuntView lv = new wordhuntView(this,game);

		if(synch != null) {
			synch.abort();
		}
		synch = new Synchronizer();
		synch.setCounter(game);
		synch.addEvent(lv);
		synch.setFinalizer(this);

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.FILL_PARENT);
		setContentView(lv,lp);
		lv.setKeepScreenOn(true);
		lv.setFocusableInTouchMode(true);
	}

	public void onResume() {
		super.onResume();
		if(game == null) newGame();

		switch(game.getStatus()) {
			case GAME_STARTING:
				game.start();
				synch.start();
			break;
			case GAME_PAUSED:
				game.unpause();
				synch.start();
			break;
			case GAME_FINISHED:
				score();
			break;
		}
	}

	public void doFinalEvent() {
		score();
	}

	private void score() {
		synch.abort();

		Bundle bun = new Bundle();
	game.save(new GameSaverTransient(bun));

		Intent scoreIntent = new Intent("com.wordhunt.wordhunt.action.SCORE");
		scoreIntent.putExtras(bun);

		startActivity(scoreIntent);

		finish();
	}


}
