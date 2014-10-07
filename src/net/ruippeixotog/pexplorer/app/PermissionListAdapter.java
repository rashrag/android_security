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

import java.util.List;

import net.ruippeixotog.pexplorer.R;
import net.ruippeixotog.pexplorer.data.PermissionCatalog;
import net.ruippeixotog.pexplorer.utils.PermissionUtils;
import net.ruippeixotog.utils.android.SimpleListAdapter;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.view.View;
import android.widget.TextView;

public class PermissionListAdapter extends SimpleListAdapter<PermissionInfo> {

	PermissionCatalog catalog;

	public PermissionListAdapter(Context context, List<PermissionInfo> objects) {
		super(context, R.layout.perm_row, objects);
		catalog = PermissionCatalog.getInstance(context);
	}

	@Override
	protected View getView(View inflatedView, PermissionInfo perm,
			boolean justInflated) {
		String shortName = PermissionUtils.getShortName(perm);

		TextView tv = (TextView) inflatedView.findViewById(android.R.id.text1);
		tv.setText(shortName);

		tv = (TextView) inflatedView.findViewById(android.R.id.text2);
		tv.setText(perm.loadLabel(catalog.getPackageManager()));

		return inflatedView;
	}

	@Override
	protected boolean isFilterMatch(CharSequence constraint, PermissionInfo perm) {
		String[] prefixes = constraint.toString().toLowerCase().split(" ");
		String[] words = PermissionUtils.getShortName(perm).split("_");

		for (String prefix : prefixes) {
			boolean hasPrefix = false;
			for (String word : words)
				if (word.toLowerCase().startsWith(prefix)) {
					hasPrefix = true;
					break;
				}
			if (!hasPrefix)
				return false;
		}
		return true;
	}
}