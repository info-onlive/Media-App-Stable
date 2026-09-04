package com.github.libretube.ui.sheets

import com.github.libretube.R
import com.github.libretube.obj.BottomSheetItem

class QuickActionsBottomSheet(
    private val onAction: (Action) -> Unit
) : BaseBottomSheet() {

    enum class Action {
        PASTE_LINK,
        SEARCH,
        DOWNLOAD_LINK
    }

    init {
        setTitle("Acciones rápidas")

        setItems(
            listOf(
                BottomSheetItem(
                    title = "Pegar enlace",
                    drawable = R.drawable.ic_share
                ),
                BottomSheetItem(
                    title = "Buscar video",
                    drawable = R.drawable.ic_search_toolbar
                ),
                BottomSheetItem(
                    title = "Descargar por enlace",
                    drawable = R.drawable.ic_download
                )
            )
        ) { index ->
            when (index) {
                0 -> onAction(Action.PASTE_LINK)
                1 -> onAction(Action.SEARCH)
                2 -> onAction(Action.DOWNLOAD_LINK)
            }
        }
    }
}
