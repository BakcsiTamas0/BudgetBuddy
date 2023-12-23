package com.example.budgetbuddy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.Adapters.CustomIncomeSpinnerAdapter
import com.example.budgetbuddy.Adapters.IncomeRecycleViewAdapter
import com.example.budgetbuddy.DataClasses.IncomeItem
import com.example.budgetbuddy.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var incomeList: MutableList<IncomeItem>
    private lateinit var addButton: Button
    private lateinit var recyclerViewAdapter: IncomeRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_income, container, false)
        spinner = view.findViewById(R.id.incomeSpinner)

        val items = listOf(
            IncomeItem("Salary", R.drawable.salary),
            IncomeItem("Crypto", R.drawable.crypto),
            IncomeItem("Rental", R.drawable.rent),
            IncomeItem("Profit", R.drawable.profit),
            IncomeItem("Other", R.drawable.other)
        )

        val adapter = CustomIncomeSpinnerAdapter(requireContext(), items)
        spinner.adapter = adapter

        incomeList = mutableListOf()

        recyclerViewAdapter = IncomeRecycleViewAdapter(requireContext(), incomeList)

        recyclerView = view.findViewById(R.id.incomeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewAdapter

        addButton = view.findViewById(R.id.addIncome)
        addButton.setOnClickListener() {
            val selectedIncome = spinner.selectedItem as IncomeItem
            incomeList.add(selectedIncome)
            recyclerViewAdapter.notifyItemInserted(incomeList.size - 1)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}