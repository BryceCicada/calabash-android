package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TabHost;
import android.widget.TextView;

public class We7SelectTabByContentDescription extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

    String tabName = args[0];

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      InstrumentationBackend.log("No tab host found");
      return new Result(false, "No TabHost found - am I on All Stations page?");
    }

    InstrumentationBackend.log("Tab host found");

    int numberOfTabs = tabHost.getTabWidget().getTabCount();
    
    
    for (int i = 0; i < numberOfTabs; i++) {
      
      tabHost.setCurrentTab(i);
      
      tabHost.getCurrentTabView().getContentDescription()
      
    }
    
    
    // goto tab
    InstrumentationBackend.solo.clickOnText(tabName);

    int listIndex = getListViewIndex(tabName);

    if (listIndex == -1) {
      return new Result(false, "Could not find list.");
    }

    ArrayList<TextView> itemText = InstrumentationBackend.solo.clickInList(itemIndex, listIndex);

    InstrumentationBackend.log("Text of clicked item:");
    for (int i = 0 ; i < itemText.size(); i++) {
    	
      TextView tv = itemText.get(i);
    	
      if (tv.getContentDescription()!= null && tv.getContentDescription().toString().equals("playable title")){
          InstrumentationBackend.log("Found playable title TextView");
       	  setValue(LAST_CLICK_TEXT, tv.getText().toString());
      }
    	
      InstrumentationBackend.log(itemText.get(i).getText().toString());
      
    }

    return new Result(true);

  }

  @Override
  public String key() {
    return "select_item_from_tab_list";
  }

}
