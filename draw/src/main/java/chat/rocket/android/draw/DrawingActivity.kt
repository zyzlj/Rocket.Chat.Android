package chat.rocket.android.draw

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_drawing.*
import kotlinx.android.synthetic.main.color_palette_view.*
import java.io.ByteArrayOutputStream


class DrawingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        image_close_drawing.setOnClickListener {
            finish()
        }
        image_send_drawing.setOnClickListener {
            val bStream = ByteArrayOutputStream()
            val bitmap = custom_draw_view.getBitmap()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
            val byteArray = bStream.toByteArray()
            val returnIntent = Intent()
            returnIntent.putExtra("bitmap", byteArray)
            setResult(Activity.RESULT_OK,returnIntent)
            finish()
        }

        setUpDrawTools()

        colorSelector()

        setPaintAlpha()

        setPaintWidth()
    }

    private fun setUpDrawTools() {
        image_draw_eraser.setOnClickListener {
            custom_draw_view.clearCanvas()
            toggleDrawTools(draw_tools,false)
        }
        image_draw_width.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && seekBar_width.isVisible){
                toggleDrawTools(draw_tools,false)
            }
            seekBar_width.isVisible = true
            seekBar_opacity.isVisible = false
            draw_color_palette.isVisible = false
        }
        image_draw_opacity.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && seekBar_opacity.isVisible){
                toggleDrawTools(draw_tools,false)
            }
            seekBar_width.isVisible = false
            seekBar_opacity.isVisible = true
            draw_color_palette.isVisible = false
        }
        image_draw_color.setOnClickListener {
            if (draw_tools.translationY == (56).toPx){
                toggleDrawTools(draw_tools,true)
            }else if (draw_tools.translationY == (0).toPx && draw_color_palette.isVisible){
                toggleDrawTools(draw_tools,false)
            }
            seekBar_width.isVisible = false
            seekBar_opacity.isVisible = false
            draw_color_palette.isVisible = true
        }
        image_draw_undo.setOnClickListener {
            custom_draw_view.undo()
            toggleDrawTools(draw_tools,false)
        }
        image_draw_redo.setOnClickListener {
            custom_draw_view.redo()
            toggleDrawTools(draw_tools,false)
        }
    }

    private fun toggleDrawTools(view: View, showView: Boolean = true) {
        if (showView){
            view.animate().translationY((0).toPx)
        }else{
            view.animate().translationY((56).toPx)
        }
    }

    private fun colorSelector() {
        image_color_black.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_black,null))
            scaleColorView(image_color_black)
        }
        image_color_red.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_red,null))
            scaleColorView(image_color_red)
        }
        image_color_yellow.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_yellow,null))
            scaleColorView(image_color_yellow)
        }
        image_color_green.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_green,null))
            scaleColorView(image_color_green)
        }
        image_color_blue.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_blue,null))
            scaleColorView(image_color_blue)
        }
        image_color_pink.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_pink,null))
            scaleColorView(image_color_pink)
        }
        image_color_brown.setOnClickListener {
            custom_draw_view.setColor(ResourcesCompat.getColor(resources, R.color.color_brown,null))
            scaleColorView(image_color_brown)
        }
    }

    private fun scaleColorView(view: View) {
        //reset scale of all views
        image_color_black.scaleX = 1f
        image_color_black.scaleY = 1f

        image_color_red.scaleX = 1f
        image_color_red.scaleY = 1f

        image_color_yellow.scaleX = 1f
        image_color_yellow.scaleY = 1f

        image_color_green.scaleX = 1f
        image_color_green.scaleY = 1f

        image_color_blue.scaleX = 1f
        image_color_blue.scaleY = 1f

        image_color_pink.scaleX = 1f
        image_color_pink.scaleY = 1f

        image_color_brown.scaleX = 1f
        image_color_brown.scaleY = 1f

        //set scale of selected view
        view.scaleX = 1.5f
        view.scaleY = 1.5f
    }

    private fun setPaintWidth() {
        seekBar_width.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                custom_draw_view.setStrokeWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setPaintAlpha() {
        seekBar_opacity.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                custom_draw_view.setAlpha(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private val Int.toPx: Float
        get() = (this * Resources.getSystem().displayMetrics.density)
}
