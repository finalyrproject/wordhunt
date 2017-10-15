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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class wordhunt extends Activity {

	@SuppressWarnings("unused")
	protected static final String TAG = "wordhunt";

	private static final int DIALOG_NO_SAVED = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		try {
       		super.onCreate(savedInstanceState);
			splashScreen();
		} catch (Exception e) {
			// Log.e(TAG,"top level",e);
		}
    }

	private void splashScreen() {
		setContentView(R.layout.splash);

		Button b = (Button) findViewById(R.id.new_game);
		// Log.d(TAG,"b="+b);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent("com.wordhunt.wordhunt.action.NEW_GAME"));
			}
		});


		b = (Button) findViewById(R.id.preferences);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new 
					Intent("com.wordhunt.wordhunt.action.CONFIGURE"));
			}
		});
	}

	public void onPause() {
		super.onPause();
		// Log.d(TAG,"Pausing");
	}

	public void onResume() {
		super.onResume();
		splashScreen();
	}


}
