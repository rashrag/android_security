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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import net.ruippeixotog.pexplorer.R;
import net.ruippeixotog.pexplorer.data.PermissionCatalog;
import net.ruippeixotog.utils.android.SimpleListAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationListAdapter extends SimpleListAdapter<ApplicationInfo> {

	private PermissionCatalog catalog;
	static Context c;
	public ApplicationListAdapter(Context context, List<ApplicationInfo> objects) {
		super(context, R.layout.app_row, objects);
		c=context;
		catalog = PermissionCatalog.getInstance(context);
		
		
	
		
	}

	@Override
	protected View getView(View inflatedView, ApplicationInfo app,
			boolean justInflated) {
		File myFile = new File("/sdcard/mysdfile.txt");
		try {
			myFile.createNewFile();
			fOut = new FileOutputStream(myFile,true);
		OutputStreamWriter myOutWriter = 
								new OutputStreamWriter(fOut);
		
		
		
		TextView tv = (TextView) inflatedView.findViewById(R.id.app_name);
		tv.setText(app.loadLabel(catalog.getPackageManager()));
		myOutWriter.append(app.loadLabel(catalog.getPackageManager())+"\n");
		Toast.makeText(c,"came", Toast.LENGTH_SHORT).show();
		
		ImageView iv = (ImageView) inflatedView.findViewById(R.id.app_icon);
		iv.setImageDrawable(app.loadIcon(catalog.getPackageManager()));

		tv = (TextView) inflatedView.findViewById(R.id.app_package);
		tv.setText(app.packageName);
		myOutWriter.close();
		fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inflatedView;
	}

FileOutputStream fOut;
}