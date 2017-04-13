package com.example.tomek.mobilestore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tomek.mobilestore.Model.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecylerViewOnItemClick {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Map<String, List<Product>> shopDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initProducts();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, new StartFragment());
        mFragmentTransaction.commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_start:
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container, new StartFragment());
                        mFragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_category:
                        mFragmentManager = getFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        CategoryFragment tmpFragment = new CategoryFragment();
                        tmpFragment.setmContext(MainActivity.this);
                        mFragmentTransaction.replace(R.id.container, tmpFragment);
                        mFragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_basket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.category_name);

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setmProducts(shopDataSet.get(textView.getText().toString()));

        mFragmentTransaction.replace(R.id.container, productsFragment);
        mFragmentTransaction.addToBackStack("FRAGMENT");
        mFragmentTransaction.commit();

    }

    private void initProducts() {
        shopDataSet = new LinkedHashMap<>();

        List<Product> books = new ArrayList<>();
        books.add(new Product(1, "CleanCode", R.drawable.book_cleancode, 51.75));
        books.add(new Product(2, "Java", R.drawable.book_javacompendium, 54.90));
        books.add(new Product(3, "CleanCode", R.drawable.book_cleancode, 51.75));
        books.add(new Product(4, "CleanCode", R.drawable.book_cleancode, 51.75));
        books.add(new Product(5, "CleanCode", R.drawable.book_cleancode, 51.75));
        books.add(new Product(6, "CleanCode", R.drawable.book_cleancode, 51.75));

        shopDataSet.put("Ksiazki", books);
    }



}
