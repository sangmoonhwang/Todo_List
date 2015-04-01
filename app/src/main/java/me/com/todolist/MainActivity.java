package me.com.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> todoListItems = new ArrayList<String>();

    ArrayAdapter<String> todoListAdapter;

    final Context context = this;

    private ListView todoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = (ListView) findViewById(R.id.todo_List);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "bKsXAwHYZdwYjGxM111xxyceVtN3tHjykbgvMwVF", "xgM4zSisrXaEUHdRERMdxzKlnlASb0zXSJRtppI9");

       // SQLiteDatabase toDoDB = openOrCreateDatabase("me.sm.ToDoListDB.db",MODE_PRIVATE, null);

        //toDoDB.execSQL("CREATE TABLE IF NOT EXISTS ToDoItems (Items varchar(100));");


        todoListAdapter = new ArrayAdapter<String>(this, R.layout.todo_item, getToDoItems());

        todoList = (ListView) findViewById(R.id.todo_List);

        todoList.setAdapter(todoListAdapter);

        Button addButton = (Button) findViewById(R.id.add_button);

        final Intent intent = new Intent(this, AddToDoItem.class);

        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(intent);
                    }
                }
        );

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // do stuff here
                final String item = ((TextView) v.findViewById(R.id.item)).getText().toString();

                new AlertDialog.Builder(context)
                        .setMessage(
                                String.format("Would you like to delete the todo item: %s",
                                        item))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff when the user clicks yes
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("TodoObject");
                                ArrayList<ParseObject> obj = new ArrayList<ParseObject>();
                                try {
                                    obj = (ArrayList) query.whereEqualTo("todo", item).find();
                                } catch (com.parse.ParseException e) {
                                    e.printStackTrace();
                                }

                                obj.get(0).deleteInBackground();

                                todoListAdapter.clear();
                                todoListAdapter.addAll(getToDoItems());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff when the user clicks no

                            }
                        })
                        .show();
            }
        });

        Button refreshButton = (Button) findViewById(R.id.refresh_button);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                todoListAdapter.clear();
                todoListAdapter.addAll(getToDoItems());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoListAdapter.clear();
        todoListAdapter.addAll(getToDoItems());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getToDoItems(){
        ArrayList<String> todoListItems = new ArrayList<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TodoObject");

        ArrayList<ParseObject> todoList = new ArrayList<ParseObject>();
        try {
            todoList = (ArrayList) query.find();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        for(ParseObject todoItem : todoList) {
            String task = todoItem.getString("todo");
            todoListItems.add(task);
        }

//        SQLiteDatabase toDoDB = openOrCreateDatabase("ToDoListDB.db", MODE_PRIVATE, null);
  //      Cursor items = toDoDB.rawQuery("SELECT Items FROM ToDoItems",null);
     //   items.moveToFirst();
       // while(!items.isAfterLast()){
         //   todoListItems.add(items.getString(0));
       //     items.moveToNext();
        //}
        return todoListItems;
    }
}
