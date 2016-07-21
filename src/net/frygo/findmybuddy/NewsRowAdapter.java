package net.frygo.findmybuddy;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import android.app.Activity;
import android.content.Context;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsRowAdapter extends ArrayAdapter<Item> implements Filterable {

	private Activity activity;
	public List<Item> items;
	public List<Item> subItems;
	private Item objBean;
	private int row;
	private DisplayImageOptions options;
	ImageLoader imageLoader;
	BuddyFilter filter;
	public NewsRowAdapter(Activity act, int resource, List<Item> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;
	options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory()
				.cacheOnDisc().build();
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		holder.tvName = (TextView) view.findViewById(R.id.tvname);
		holder.tvEmail = (TextView) view.findViewById(R.id.tvemail);
		holder.imgView = (ImageView) view.findViewById(R.id.image);
		holder.pbar = (ProgressBar) view.findViewById(R.id.pbar);
	
	

		if (holder.tvName != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvName.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.tvEmail != null && null != objBean.getEmail()
				&& objBean.getEmail().trim().length() > 0) {
			holder.tvEmail.setText(Html.fromHtml(objBean.getEmail()));
			
		}
		holder.imgView.setImageResource(R.drawable.logo);
	
		
	if (holder.imgView != null) {
			if (null != objBean.getLink()
					&& objBean.getLink().trim().length() > 0) {
				final ProgressBar pbar = holder.pbar;

				imageLoader.init(ImageLoaderConfiguration
						.createDefault(activity));
				imageLoader.displayImage(objBean.getLink(), holder.imgView,
						options, new ImageLoadingListener() {
							@Override
							public void onLoadingComplete() {
								pbar.setVisibility(View.INVISIBLE);

							}

							@Override
							public void onLoadingFailed() {
								pbar.setVisibility(View.INVISIBLE);
							}

							@Override
							public void onLoadingStarted() {
								pbar.setVisibility(View.VISIBLE);

							}
						});

			} else {

			}
		}

		return view;
	}

	

	public class ViewHolder {
		public TextView tvName, tvEmail;
		public ImageView imgView;
		public ProgressBar pbar;
	}
	 @Override
     public Filter getFilter() {
         if (filter == null){
           filter  = new BuddyFilter();
         }
         return filter;
       }

	  private class BuddyFilter extends Filter{


	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix,
	                              FilterResults results) {
		 if (results.count == 0)
		        notifyDataSetInvalidated();
		    else {
		    	 items =  (List<Item>)results.values;
		  	   notifyDataSetChanged();
		    }
	
	  
	    
	   	}


	protected FilterResults performFiltering(CharSequence prefixx) {
	      // NOTE: this function is *always* called from a background thread, and
	      // not the UI thread. 
		
		String prefix=prefixx.toString();
		
	      FilterResults results = new FilterResults();
	      List<Item> i = new ArrayList<Item>(items);

	      if (prefix!= null && prefix.length() > 0) {

	    	  for (int index = 0; index < items.size(); index++) {
	             Item si = items.get(index);
	             
	              // if you compare the Strings like you did it will never work as you compare the full item string(you'll have a match only when you write the EXACT word)
	              // keep in mind that you take in consideration capital letters!
	              if(si.getEmail().toString().contains(prefix)) {
	                i.add(si);  
	              }
	          }
	          results.values = i;
	          results.count = i.size();  
	          
	      }
	      else{
	          
	              results.values = items;
	              results.count = items.size();
	          
	      }

	      return results;
	   }
	 }     
}//end of newsRowAdapter

