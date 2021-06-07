/*
 * Copyright 2013 Thomas Hoffmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.habitos_saludables_g10.widget;

import android.content.Intent;

import com.example.habitos_saludables_g10.R;
import com.example.habitos_saludables_g10.podometro.Database;
import com.example.habitos_saludables_g10.ui.Activity_Main;
import com.example.habitos_saludables_g10.ui.Fragment_Overview;
import com.example.habitos_saludables_g10.util.Util;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;



/**
 * Class for providing a DashClock (https://code.google.com/p/dashclock)
 * extension
 */
public class DashClock extends DashClockExtension {

    @Override
    protected void onUpdateData(int reason) {
        ExtensionData data = new ExtensionData();
        Database db = Database.getInstance(this);
        int steps = Math.max(db.getCurrentSteps() + db.getSteps(Util.getToday()), 0);
        data.visible(true).status(Fragment_Overview.formatter.format(steps))
                .icon(R.mipmap.ic_dashclock)
                .clickIntent(new Intent(DashClock.this, Activity_Main.class));
        db.close();
        publishUpdate(data);
    }

}
