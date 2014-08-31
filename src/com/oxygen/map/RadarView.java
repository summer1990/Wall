package com.oxygen.map;

import com.oxygen.wall.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
* @ClassName RadarView
* @Description 绘制雷达View动画
* @author oxygen
* @email oxygen0106@163.com
* @date 2014-8-25 下午10:41:10
*/
public class RadarView extends View {

	private int TextSize;
	private Paint markerPaint;
	private Paint textPaint;
	private Paint circlePaint;
	private Paint linePaint;
	private Paint sweepPaint;
	SweepGradient sweepGradient = null;//扇形渐变Shader
	int num = 0;
	
	
	
	
	
	/**
	 * Constructor
	 * @param context
	 */
	public RadarView(Context context){
		super(context);
	}
	
	/**
	 * Constructor
	 * @param context
	 * @param att
	 */
	public RadarView(Context context, AttributeSet att){
		this(context, att, 0);
				
		initMyView();
	}
	
	/**
	 * Constructor
	 * @param context
	 * @param att
	 * @param defStyle
	 */
	public RadarView(Context context, AttributeSet att, int defStyle){
		super(context,att,defStyle);
		initMyView();
	}
	
		/**
		* @param 
		* @return void
		* @Description //初始化view,将此方法加入构造函数中，减少内存频繁分配  
		*/
	private void initMyView(){
		Resources r = this.getResources();
			
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//圆形画笔，设置Paint为抗锯齿
		circlePaint.setColor(Color.GRAY);//设置Paint颜色
		circlePaint.setStrokeWidth(2);//轮廓宽度
		circlePaint.setStyle(Paint.Style.STROKE);
//		circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充并画出轮廓
//		circlePaint.setPathEffect(PathEffect effect);  //设置绘制路径的效果，如点画线等 
//			circlePaint.setSrokeJoin(Paint.Join join);//设置绘制时各图形的结合方式，如平滑效果等
			
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//线性画笔
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setColor(Color.GRAY);
		linePaint.setStrokeWidth(2);
			
		sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//雷达Shader画笔
		sweepPaint.setStrokeCap(Paint.Cap.ROUND);
		sweepPaint.setStrokeWidth(4);
//		sweepPaint.setAlpha(150);
		sweepGradient = new SweepGradient(0,0,r.getColor(R.color.start_color),r.getColor(R.color.end_color));
		sweepPaint.setShader(sweepGradient);
			
	}
	
	@Override
	protected void onMeasure(int wMeasureSpec, int hMeasureSpec){
		int width = measure(wMeasureSpec);
		int height = measure(hMeasureSpec);
		int d = (width>=height)?height:width;//获取最短的边作为直径
		setMeasuredDimension(d,d);//onMeasure必须调用该方法来确定绘制控件面积
	}
	
	/**
	* @param @param measureSpec
	* @param @return
	* @return int
	* @Description 测量可获得的长度，满足自定义长度或不满足自定义时按照测得返回长度  
	*/
	private int measure(int measureSpec){
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if(specMode==MeasureSpec.UNSPECIFIED){//parent对绘制面积无限制
			result = 200;
		}else if(specMode==MeasureSpec.AT_MOST){//给出了可绘制的最大面积界限
			 if(specSize>=200){//与界限比较
				 result = 200;
			 }else{
				 result = specSize;
			 }
		}else {//MeasureSpec.EXACTLY 给出了明确的限定范围
			result = specSize;
		}
		return result;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
		int Width = getMeasuredWidth();////计算控件的中心位置。Measured不是Measure，该方法属于View类
		int Height = getMeasuredHeight();
		int pointX =  Width/2;//获得圆心坐标
		int pointY = Height/2;
		int radius = (pointX>=pointY) ? pointY : pointX;//设置半径
		
		radius -= 10;
		canvas.save();//保存Canvas坐标矩阵
		
		num = num + 5;
		canvas.translate(pointX, pointY);//设置旋转的原点
		canvas.rotate(270 + num);
		canvas.drawCircle(0, 0, radius, sweepPaint);//绘制扫描区域
			
		canvas.restore();//恢复原Canvas坐标矩阵
		
		canvas.drawCircle(pointX, pointY, radius, circlePaint);//绘制3个嵌套圆形，使用circlePaint画笔
		canvas.drawCircle(pointX, pointY, radius*2/3, circlePaint);
		canvas.drawCircle(pointX, pointY, radius/3, circlePaint);
		
		
		canvas.drawLine(radius + 10, 10, radius +10, 2*radius + 10, linePaint);//绘制十字分割线 ， 竖线
		canvas.drawLine(10, radius + 10, 2*radius + 10, radius + 10, linePaint);
		
		canvas.translate(pointX, pointY);//设置相对原点为圆心坐标
		
		int g = radius/12;//最小刻度X轴
		
		float[] ptsX={-g,-8,-g,8,  
                -2*g,-15,-2*g,15,  
                -3*g,-8,-3*g,8,  

                -5*g,-8,-5*g,8,
                -6*g,-15,-6*g,15,
                -7*g,-8,-7*g,8,

                -9*g,-8,-9*g,8,
                -10*g,-15,-10*g,15,
                -11*g,-8,-11*g,8,
				
				g,-8,g,8,  
                2*g,-15,2*g,15,  
                3*g,-8,3*g,8,  

                5*g,-8,5*g,8,
                6*g,-15,6*g,15,
                7*g,-8,7*g,8,

                9*g,-8,9*g,8,
                10*g,-15,10*g,15,
                11*g,-8,11*g,8};
		
		canvas.drawLines(ptsX, linePaint);//绘制X轴刻度
		
		float[] ptsY={-8,-g,8,-g,  
				-15,-2*g,15,-2*g,  
                -8,-3*g,8,-3*g,

                -8,-5*g,8,-5*g,  
				-15,-6*g,15,-6*g,  
                -8,-7*g,8,-7*g,

                -8,-9*g,8,-9*g,  
				-15,-10*g,15,-10*g,  
                -8,-11*g,8,-11*g,
				
                -8,g,8,g,  
				-15,2*g,15,2*g,  
                -8,3*g,8,3*g,  

                -8,5*g,8,5*g,  
				-15,6*g,15,6*g,  
                -8,7*g,8,7*g,

                -8,9*g,8,9*g,  
				-15,10*g,15,10*g,  
                -8,11*g,8,11*g};
		
		canvas.drawLines(ptsY, linePaint);//绘制Y轴刻度		
	}
	
}

