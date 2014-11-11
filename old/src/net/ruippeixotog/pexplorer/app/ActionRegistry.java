/*
 * Copyright (C) 2012 Rui Gonçalves and Daniel Cibrão
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ruippeixotog.pexplorer.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;
import net.ruippeixotog.pexplorer.actions.AccessFineLocationAction;
import net.ruippeixotog.pexplorer.actions.AccessNetworkStateAction;
import net.ruippeixotog.pexplorer.actions.ChangeWifiStateAction;
import net.ruippeixotog.pexplorer.actions.GetAccountsAction;
import net.ruippeixotog.pexplorer.actions.InternetAccessAction;
import net.ruippeixotog.pexplorer.actions.PhoneCallAction;
import net.ruippeixotog.pexplorer.actions.ReadCalendarAction;
import net.ruippeixotog.pexplorer.actions.ReadContactsAction;
import net.ruippeixotog.pexplorer.actions.ReadPhoneStateAction;
import net.ruippeixotog.pexplorer.actions.RebootAction;
import net.ruippeixotog.pexplorer.actions.RetrieveRunningTasksAction;
import net.ruippeixotog.pexplorer.actions.SendTestSmsAction;
import net.ruippeixotog.pexplorer.actions.ShellCommandAction;
import net.ruippeixotog.pexplorer.actions.TakePictureAction;
import net.ruippeixotog.pexplorer.actions.VibrateAction;
import net.ruippeixotog.pexplorer.actions.WriteCalendarAction;
import net.ruippeixotog.pexplorer.actions.WriteExternalStorageAction;
import net.ruippeixotog.pexplorer.actions.WriteSettingsAction;

public class ActionRegistry {

	private static ActionRegistry instance;

	public static ActionRegistry getInstance() {
		if (instance == null)
			instance = new ActionRegistry();
		return instance;
	}

	private Map<String, List<PermissionAction>> actions = new HashMap<String, List<PermissionAction>>();

	// register all actions implemented for each permission
	static {
		getInstance().addAction("android.permission.SEND_SMS",
				new SendTestSmsAction());
		getInstance().addAction("android.permission.CALL_PHONE",
				new PhoneCallAction());
		getInstance().addAction("android.permission.READ_CONTACTS",
				new ReadContactsAction());
		getInstance().addAction("android.permission.READ_PHONE_STATE",
				new ReadPhoneStateAction());
		getInstance().addAction("android.permission.ACCESS_FINE_LOCATION",
				new AccessFineLocationAction());
		getInstance().addAction("android.permission.INTERNET",
				new InternetAccessAction());
		getInstance().addAction("android.permission.ACCESS_NETWORK_STATE",
				new AccessNetworkStateAction());
		getInstance().addAction("android.permission.CHANGE_WIFI_STATE",
				new ChangeWifiStateAction());
		getInstance().addAction("android.permission.WRITE_SETTINGS",
				new WriteSettingsAction());
		getInstance().addAction("android.permission.WRITE_EXTERNAL_STORAGE",
				new WriteExternalStorageAction());
		getInstance().addAction("android.permission.VIBRATE",
				new VibrateAction());
		getInstance().addAction("android.permission.CAMERA",
				new TakePictureAction());
		getInstance().addAction("android.permission.READ_CALENDAR",
				new ReadCalendarAction());
		getInstance().addAction("android.permission.WRITE_CALENDAR",
				new WriteCalendarAction());
		getInstance().addAction("android.permission.GET_TASKS",
				new RetrieveRunningTasksAction());
		getInstance().addAction("android.permission.GET_ACCOUNTS",
				new GetAccountsAction());
		getInstance().addAction("com.noshufou.android.su.provider.READ",
				new RebootAction(), new ShellCommandAction());
		getInstance().addAction("com.noshufou.android.su.provider.WRITE",
				new RebootAction(), new ShellCommandAction());
	}

	private ActionRegistry() {
	}

	public List<PermissionAction> getPermissionActions(String permName)
	{
		
		if (actions.containsKey(permName))
			return actions.get(permName);

		List<PermissionAction> permActions = new ArrayList<PermissionAction>();
		actions.put(permName, permActions);
		return permActions;
	}

	public void addAction(String permName, PermissionAction... actions) {
		for (PermissionAction action : actions)
			addAction(permName, action, -1);
	}

	public void addAction(String permName, PermissionAction action, int position) {
		List<PermissionAction> permActions;
		if (!actions.containsKey(permName)) {
			permActions = new ArrayList<PermissionAction>();
			actions.put(permName, permActions);
		} else
			permActions = actions.get(permName);

		if (position >= 0)
			permActions.add(position, action);
		else
			permActions.add(action);
	}
}
