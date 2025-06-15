import androidx.paging.PagingData
import com.example.nbaplayers.ui.model.PlayerUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PlayersScreenState(
    val players: Flow<PagingData<PlayerUiModel>> = emptyFlow()
)
