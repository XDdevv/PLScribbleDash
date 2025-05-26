package zed.rainxch.plscribbledash.game.domain.use_case

import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.game.domain.model.ParsedPath
import zed.rainxch.plscribbledash.game.domain.utils.VectorDrawableParser
import javax.inject.Inject

class GetRandomPathDataUseCase @Inject constructor(
    private val vectorDrawableParser: VectorDrawableParser
) {
    operator fun invoke(): ParsedPath {
        return getPathDataList().random()
    }

    private fun getPathDataList(): List<ParsedPath> {
        return getResources().map { resId ->
            vectorDrawableParser.getPathData(resId)
        }
    }

    private fun getResources(): List<Int> {
        return listOf(
            R.drawable.alien,
            R.drawable.bicycle,
            R.drawable.boat,
            R.drawable.book,
            R.drawable.butterfly,
            R.drawable.camera,
            R.drawable.car,
            R.drawable.castle,
            R.drawable.cat,
            R.drawable.clock,
            R.drawable.crown,
            R.drawable.cup,
            R.drawable.dog,
            R.drawable.envelope,
            R.drawable.eye,
            R.drawable.fish,
            R.drawable.flower,
            R.drawable.football_field,
            R.drawable.frog,
            R.drawable.glasses,
            R.drawable.heart,
            R.drawable.helicotper,
            R.drawable.hotairballoon,
            R.drawable.house,
            R.drawable.moon,
            R.drawable.mountains,
            R.drawable.robot,
            R.drawable.rocket,
            R.drawable.smiley,
            R.drawable.snowflake,
            R.drawable.sofa,
            R.drawable.star,
            R.drawable.train,
            R.drawable.umbrella,
            R.drawable.whale
        )
    }
}