package com.example.myview.view;


import com.example.myview.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * �Զ���ؼ��Ĳ���   ��������˳��1.���캯��  2.onMeasure 3.onDraw
 * 1.��д�����������Ĺ��캯��
 * 2.��д onMeasure����
 * 3.onDraw
 * @author Administrator
 *
 */
public class SlidingButton extends View {
	private Bitmap gray_bg;//��ɫ����
	private Bitmap green_bg;//��ɫ����
	private Paint paint;    //����
	private Bitmap btn;   //�������ȦȦ
	private Bitmap text_bg;   //�ı��ı���ͼƬ
	private float scale_h;    //���������ű���
	private final float REAL_PER = 0.95f;  //ÿ��С��߶�ռ1/20
	private int gray_bg_height;    //���˵ĸ߶�
	private final int PRICE_PADDING = 15;
	private int max_price;   //�۸������
	private int min_price;   //�۸������
	private float span;   //���������ĵľ���
	private float half_boll;   //����ĸ߶�
	private float btn_x;    //ԲȦ��x����
	private float circle_up_y;
	private float circle_down_y;
	
	private boolean isMaxPriced,isMinPriced;    //����boolean�ı�����ȷ���Ǵ�������һ��ԲȦ
	
	private final int FIRST_STAGE = 0;
	private final int SECOND_STAGE = 200;
	private final int THIRD_STAGE = 500;
	private final int FOUTH_STAGE = 1000;
	private final int FIFTH_STAGE = 10000;

	
	public SlidingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.myviewattrs);
		max_price=array.getInt(R.styleable.myviewattrs_max_price, 1000);
		min_price=array.getInt(R.styleable.myviewattrs_min_price, 200);
	}
	
	private void initView() {
		gray_bg=BitmapFactory.decodeResource(getResources(), R.drawable.axis_before);
		green_bg=BitmapFactory.decodeResource(getResources(), R.drawable.axis_after);
		btn=BitmapFactory.decodeResource(getResources(), R.drawable.btn);
		text_bg=BitmapFactory.decodeResource(getResources(), R.drawable.bg_number);
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//Ĭ�ϵĶ�Ϊfill_parent
		int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);     //�õ��ؼ���ʵ�ʿ�
		int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);	//�õ��ؼ���ʵ�ʸ�
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        
        
        //wrap_content  ��ΪͼƬ�ĸ߶�
        gray_bg_height = gray_bg.getHeight();
        
        
        //MeasureSpec.EXACTLY  ����fill_parent �;����xx dipֵ
        // MeasureSpec.AT_MOST   ����wrap_content
        int measuredHeight=heightMode==MeasureSpec.EXACTLY?sizeHeight:gray_bg_height;  //�������õĸ߶�
    
        scale_h=(float)measuredHeight/gray_bg_height;
        span=REAL_PER*gray_bg_height/4;
        half_boll=gray_bg_height*(1-REAL_PER)/2;
        int measuredWidth=2*measuredHeight/3;   //���õĿ��
        setMeasuredDimension(measuredWidth, measuredHeight);
	//	super.onMeasure(widthMeasureSpec, heightMeasureSpec);   //���ø���Ĭ�ϵĲ�������
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.save() ��canvas.restore()���ǳɶ�
		canvas.save();
		canvas.scale(scale_h, scale_h);
		
		//����ͼƬ ���˵�x����
		int x=(int)((this.getWidth()/scale_h-gray_bg.getWidth())/2);
		
		//�������ұߵĿ̶��ı�
		String [] numbers = new String[]{
				String.valueOf(FIFTH_STAGE),
				String.valueOf(FOUTH_STAGE),
				String.valueOf(THIRD_STAGE),
				String.valueOf(SECOND_STAGE),
				String.valueOf(FIRST_STAGE)
		};
		
		canvas.drawBitmap(gray_bg, x, 0, null);
		
		//�������ı������С
		paint.setTextSize(20/scale_h);
		
		for(int i=0;i<numbers.length;i++){
			float scan_y=REAL_PER*gray_bg_height/4;
			//�����ı���y����
			
			//paint.descent()���ʻ������µľ���  ��������������
			//paint.ascent()  ���ʻ������ϵľ���
			float text_y=scan_y*i+(1-REAL_PER)*gray_bg_height/2+paint.descent();
			canvas.drawText(numbers[i], 5*x/4, text_y, paint);
		}
		
		btn_x = (this.getWidth()/scale_h-btn.getWidth())/2;
		
		circle_up_y = getYbyPrice(max_price);
		circle_down_y = getYbyPrice(min_price);
		//��ԲȦ
		canvas.drawBitmap(btn, btn_x, circle_up_y-btn.getHeight()/2, null);
		canvas.drawBitmap(btn, btn_x, circle_down_y-btn.getHeight()/2, null);
		
		//src�������des����  ����left=0
		//��max_price��min_price֮�����ɫ��
		//���ֻ�ͼ�ķ������൱��ͼƬ�Ĳü�
		Rect src=new Rect(0, (int)circle_up_y, green_bg.getWidth(), (int)circle_down_y);
		Rect des=new Rect(x, (int)circle_up_y, x+green_bg.getWidth(), (int)circle_down_y);
		canvas.drawBitmap(green_bg, src, des, null);
		
		
		//����ߵļ۸����
		Rect rect_up = getRectByMidLine(circle_up_y);
		canvas.drawBitmap(text_bg, null, rect_up, paint);
		Rect rect_down = getRectByMidLine(circle_down_y);
		canvas.drawBitmap(text_bg, null, rect_down, paint);
		
		//����߼۸����ı�
		//���ı���xy����
		float text_u_x = (3*rect_up.width()/4 - paint.measureText(String.valueOf(max_price)))/2;
		float text_u_y = rect_up.top - paint.ascent()+PRICE_PADDING;
		//���ı���xy����
		float text_d_x = (3*rect_down.width()/4 - paint.measureText(String.valueOf(min_price)))/2;
		float text_d_y = rect_down.top - paint.ascent()+PRICE_PADDING;
		
		//������ı�
		canvas.drawText(String.valueOf(max_price), text_u_x, text_u_y, paint);
		canvas.drawText(String.valueOf(min_price), text_d_x, text_d_y, paint);
		canvas.restore();
		super.onDraw(canvas);
	}
	/**
	 * ���ݼ۸�ı任 ���õ���ԲȦ��y����
	 * @param price
	 * @return
	 */
	public float getYbyPrice(int price){
		float y=0;
		if(price<FIRST_STAGE){
			y=0;
		}
		if(price>FIFTH_STAGE){
			y=FIFTH_STAGE;
		}
		if(price>=FIRST_STAGE && price < SECOND_STAGE){
			y=gray_bg_height-half_boll-span*price/(SECOND_STAGE-FIRST_STAGE);
		}else if(price >= SECOND_STAGE && price <THIRD_STAGE){
			y=gray_bg_height-span-half_boll-span*(price-SECOND_STAGE)/(THIRD_STAGE-SECOND_STAGE);
		}else if(price >= THIRD_STAGE && price < FOUTH_STAGE){
			y=gray_bg_height-2*span-half_boll-span*(price-THIRD_STAGE)/(FOUTH_STAGE-THIRD_STAGE);
		}else{
			y=gray_bg_height-3*span-half_boll-span*(price-FOUTH_STAGE)/(FIFTH_STAGE-FOUTH_STAGE);
		}
		return y;
	}
	
	/**
	 * ���ݴ�Ȧ������ȷ������۸��α�ľ���
	 * @param y
	 * @return
	 */
	public Rect getRectByMidLine(float y){
		Rect rect = new Rect();
		rect.left = 0;//��
		rect.right = (int) btn_x;
		//�����ı��ĸ�
		float text_h = paint.descent()-paint.ascent();
		rect.top = (int) (y - text_h/2)-PRICE_PADDING;
		rect.bottom = (int) (y+ text_h/2)+PRICE_PADDING;
		return rect;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float x=event.getX()/scale_h;  //event.getX()�õ����ǿؼ��е�x���꣬Ҫ�ж�x����߷�Χ���뽫������
			float y=event.getY()/scale_h;
			
			if(x >= btn_x && x <= btn_x+btn.getWidth()){
				//����������Ĵ�ԲȦ
				if(y >= circle_up_y-btn.getHeight()/2 && y <= circle_up_y+btn.getHeight()/2){
					isMaxPriced=true;
					isMinPriced=false;
				}
				//����������Ĵ�ԲȦ
				if(y >= circle_down_y-btn.getHeight()/2 && y <= circle_down_y+btn.getHeight()/2){
					isMinPriced=true;
					isMaxPriced=false;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float y2 = event.getY();
			if(isMaxPriced){
				max_price = getPriceByPosition(y2);
				if(max_price<min_price){
					max_price = min_price;
				}
			}
			if(isMinPriced){
				min_price = getPriceByPosition(y2);
				if(min_price>max_price){
					min_price = max_price;
				}
			}
			this.invalidate();//�ػ�
			break;
		case MotionEvent.ACTION_UP:
			isMinPriced=false;
			isMaxPriced=false;
			break;
		default:
			break;
		}
		return true;
	}
	/**
	 * ����y������õ���Ӧ�ļ۸�,�˴���ע�������ѡȡ
	 * @param y
	 * @return
	 */
	private int getPriceByPosition(float y) {
		//��Ϊ�õ���y�����ǿؼ���y���꣬���������span��half_height����this.getHeigth()
		float span = REAL_PER*this.getHeight()/4;
		float half_height = this.getHeight()*(1-REAL_PER)/2;
//		float span = REAL_PER*gray_bg_height/4;
//		float half_height = gray_bg_height*(1-REAL_PER)/2;
		int price=0;
		if(y<half_height){
			price=FIFTH_STAGE;
		}else if(y >= half_height && y < half_height+span){
			//1000-10000
			price=(int) (FIFTH_STAGE-(FIFTH_STAGE-FOUTH_STAGE)*(y-half_height)/span);
		}else if(y >= half_height+span && y< half_height+2*span){
			//500-1000
			price=(int) (FOUTH_STAGE-(FOUTH_STAGE-THIRD_STAGE)*(y-half_height-span)/span);
		}else if(y >= half_height+2*span && y< half_height+3*span){
			//200-500
			price=(int) (THIRD_STAGE-(THIRD_STAGE-SECOND_STAGE)*(y-half_height-2*span)/span);
		}else if(y >= half_height+3*span && y< half_height+4*span){
			//0-200
			price=(int) (SECOND_STAGE-(SECOND_STAGE-FIRST_STAGE)*(y-half_height-3*span)/span);
		}
		if(price < 0){
			price=0;
		}
		
		if(price >1000){ //1000-10000���£�ÿ����һ�θı�1000
			int mod=price % 1000;
			if(mod >= 500){
				price=price-mod+1000;
			}else{
				price=price-mod;
			}
		}
		if(price <1000){  //1000���£�ÿ����һ�θı�20
			int mod=price % 20;
			if(mod >= 10){
				price=price-mod+20;
			}else{
				price=price-mod;
			}
		}
		return price;
	}
}
