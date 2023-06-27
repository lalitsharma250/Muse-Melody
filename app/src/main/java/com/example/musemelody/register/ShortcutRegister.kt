package com.example.musemelody.register

import com.example.musemelody.model.media.valueObject.ShortcutItemId
import com.example.musemelody.repository.realm.ShortcutRepository

interface ShortcutRegister {
    fun exists(shortcutItemId: ShortcutItemId): Boolean {
        val repository = ShortcutRepository()

        return repository.exists(shortcutItemId)
    }

    fun add(shortcutItemId: ShortcutItemId) {
        val repository = ShortcutRepository()

        repository.add(shortcutItemId)
    }

    fun delete(shortcutItemId: ShortcutItemId) {
        val repository = ShortcutRepository()

        repository.delete(shortcutItemId)
    }
}