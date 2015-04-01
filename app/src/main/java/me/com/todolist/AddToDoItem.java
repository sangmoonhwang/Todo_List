package me.com.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;


public class AddToDoItem extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_item);

        final Intent intent = new Intent(this, MainActivity.class);

        String todoItem = ((EditText) findViewById(R.id.add_todo_text)).getText().toString();
     //   intent.putExtra("me.com.todolist.ToDoItem", todoItem);

        Button addButton = (Button) findViewById(R.id.add_todo_button);

        // adding the listner
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText item = (EditText) findViewById(R.id.add_todo_text);
                        String todoItem = item.getText().toString();

                        if (!todoItem.equals("")) {
                  //          String sql = String.format("INSERT INTO ToDoItems VALUES ('%s');", todoItem);
                            ParseObject todoObject = new ParseObject("TodoObject");
                            todoObject.put("todo", todoItem);
                            todoObject.saveInBackground();

                    //        SQLiteDatabase toDoDB = openOrCreateDatabase("me.sm.ToDoListDB.db", MODE_PRIVATE, null);
                      //      toDoDB.execSQL(sql);
                        }
       //                 startActivity(intent);
                        finish();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_to_do_item, menu);
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
}
