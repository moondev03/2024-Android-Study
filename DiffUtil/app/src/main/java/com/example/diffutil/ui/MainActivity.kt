package com.example.diffutil.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diffutil.databinding.ActivityMainBinding
import com.example.diffutil.model.Monster
import com.example.diffutil.utils.ItemTouchHelperCallback
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val monsterRvAdapter: MonsterRvAdapter by lazy { MonsterRvAdapter() }
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dataSet = arrayListOf<Monster>().apply {
        add(Monster(0, "뿔버섯", 10, listOf(100, 10, 50)))
        add(Monster(1, "스텀프", 23, listOf(200, 20, 100)))
        add(Monster(2, "슬라임", 2, listOf(10, 1, 5)))
        add(Monster(3, "주니어발록", 2500, listOf(10000, 1000, 50000)))
        add(Monster(4, "이블아이", 100, listOf(1000, 200, 1000)))
        add(Monster(5, "와일드카고", 50, listOf(2000, 250, 10000)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = monsterRvAdapter
        }

        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(binding.recyclerview))
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)

        monsterRvAdapter.submitList(dataSet)

        binding.fab.setOnClickListener {
            val list = monsterRvAdapter.currentList.toMutableList()
            list.add(dataSet[Random.nextInt(6)])
            monsterRvAdapter.submitList(list)
        }
    }
}