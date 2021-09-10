package com.michaelmccormick.musicplayer

import org.junit.Assert.assertEquals
import org.junit.Test

class ListTest {
    @Test
    fun push() {
        val list = LinkedList<Int>()
        list.push(3)
        list.push(2)
        list.push(1)
        assertEquals("1 -> 2 -> 3", list.toString())
    }

    @Test
    fun append() {
        val list = LinkedList<Int>()
        list.append(1)
        list.append(2)
        list.append(3)
        assertEquals("1 -> 2 -> 3", list.toString())
    }

    @Test
    fun insert() {
        val list = LinkedList<Int>()
        list.push(3)
        list.push(2)
        list.push(1)
        assertEquals("1 -> 2 -> 3", list.toString())

        val middleNode = list.nodeAt(1)!!
        list.insert(7, middleNode)
        assertEquals("1 -> 2 -> 7 -> 3", list.toString())
    }
}
