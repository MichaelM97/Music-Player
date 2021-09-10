package com.michaelmccormick.musicplayer

class LinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    override fun toString(): String {
        return if (isEmpty()) {
            "Empty List"
        } else {
            head.toString()
        }
    }

    fun push(value: T) {
        head = Node(value, head)
        if (tail == null) tail = head
        size++
    }

    fun append(value: T) {
        if (isEmpty()) {
            push(value)
            return
        }
        tail?.next = Node(value)
        tail = tail?.next
        size++
    }

    fun insert(value: T, afterNode: Node<T>): Node<T> {
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        val newNode = Node(value, afterNode.next)
        afterNode.next = newNode
        size++
        return newNode
    }

    fun pop() : T? {
        if (!isEmpty()) size--
        val removedValue = head?.value
        head = head?.next
        if (isEmpty()) tail = null
        return removedValue
    }

    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0

        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }

        return currentNode
    }

    private fun isEmpty() = size == 0
}

data class Node<T>(val value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        return next?.let {
            "$value -> ${next.toString()}"
        } ?: "$value"
    }
}
