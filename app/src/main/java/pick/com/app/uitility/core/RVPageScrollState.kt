package pick.com.app.uitility.core

sealed class RVPageScrollState {
    class Idle: RVPageScrollState()
    class Dragging: RVPageScrollState()
    class Settling: RVPageScrollState()
}